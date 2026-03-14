// Inject auth automatically on front-end for our local dev convenience
const authToken = "Basic " + btoa("ggigeroa:admin");

// UI Logic
function mostrarSeccion(seccionId) {
    document.querySelectorAll('.section').forEach(s => s.classList.remove('active'));
    document.getElementById(seccionId).classList.add('active');

    document.querySelectorAll('.nav-tabs button').forEach(b => b.classList.remove('active'));
    event.target.classList.add('active');
}

function mostrarRespuestaRapida(elementId, data, isError) {
    const elm = document.getElementById(elementId);
    elm.classList.add('show');
    let html = isError ?
        '<div class="status-badge status-error">❌ Operación Falló</div>' :
        '<div class="status-badge status-success">✓ Operación Exitosa</div>';

    if (data) html += '<pre>' + JSON.stringify(data, null, 2) + '</pre>';
    elm.innerHTML = html;
}

// Generic API Caller
async function apiRequest(url, method = 'GET', bodyObj = null) {
    const options = {
        method: method,
        headers: {
            'Content-Type': 'application/json',
            'Authorization': authToken
        }
    };
    if (bodyObj) options.body = JSON.stringify(bodyObj);

    try {
        const response = await fetch(url, options);

        // No content scenarios (like DELETE)
        if (response.status === 204) return { success: true, data: null };

        const data = await response.json();
        if (!response.ok) throw new Error(data.message || 'Error en servidor');

        return { success: true, data };
    } catch (error) {
        console.error("API Fetch Error:", error);
        return { success: false, error: error.message };
    }
}


// ==========================================
//  CONTROLADORES: REGISTROS
// ==========================================
function limpiarFormularioRegistro() {
    document.getElementById('regId').value = '';
    document.getElementById('regPath').value = '';
    document.getElementById('regUsuario').value = '';
    document.getElementById('btnGuardarRegistro').innerText = '✅ Crear Registro';
}

async function listarRegistros() {
    const res = await apiRequest('/api/registros');
    if (res.success) {
        const tbody = document.querySelector('#tablaRegistros tbody');
        tbody.innerHTML = '';

        if (res.data.length === 0) {
            tbody.innerHTML = '<tr><td colspan="4">No hay registros almacenados.</td></tr>';
        } else {
            res.data.forEach(reg => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                            <td>${reg.id}</td>
                            <td>${reg.path || '-'}</td>
                            <td>${reg.idUsuario || '-'}</td>
                            <td class="actions">
                                <button class="action-btn edit" onclick='editarRegistroUI(${JSON.stringify(reg)})'>Editar</button>
                                <button class="action-btn del" onclick="eliminarRegistro(${reg.id})">Borrar</button>
                            </td>
                        `;
                tbody.appendChild(tr);
            });
        }
        document.getElementById('tablaRegistros').classList.add('show');
        document.getElementById('responseRegistros').classList.remove('show');
    } else {
        mostrarRespuestaRapida('responseRegistros', res, true);
    }
}

function editarRegistroUI(reg) {
    document.getElementById('regId').value = reg.id;
    document.getElementById('regPath').value = reg.path || '';
    document.getElementById('regUsuario').value = reg.idUsuario || '';
    document.getElementById('btnGuardarRegistro').innerText = '🔄 Actualizar Registro';
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

async function guardarRegistro() {
    const id = document.getElementById('regId').value;
    const payload = {
        path: document.getElementById('regPath').value,
        idUsuario: document.getElementById('regUsuario').value
    };

    const isEdit = id !== '';
    const method = isEdit ? 'PUT' : 'POST';
    const url = isEdit ? `/api/registros/${id}` : '/api/registros';

    const res = await apiRequest(url, method, payload);
    if (res.success) {
        limpiarFormularioRegistro();
        mostrarRespuestaRapida('responseRegistros', res.data, false);
        listarRegistros();
    } else {
        mostrarRespuestaRapida('responseRegistros', res, true);
    }
}

async function eliminarRegistro(id) {
    if (!confirm(`¿Deseas ELIMINAR el registro ID #${id}?`)) return;
    const res = await apiRequest(`/api/registros/${id}`, 'DELETE');
    if (res.success) {
        listarRegistros();
        mostrarRespuestaRapida('responseRegistros', { info: 'Registro Eliminado Exitosamente' }, false);
    } else {
        mostrarRespuestaRapida('responseRegistros', res, true);
    }
}


// ==========================================
//  CONTROLADORES: IMÁGENES
// ==========================================
function limpiarFormularioImagen() {
    document.getElementById('imgId').value = '';
    document.getElementById('imgNombre').value = '';
    document.getElementById('imgBase64').value = '';
    document.getElementById('btnGuardarImagen').innerText = '✅ Crear Imagen';
}

async function listarImagenes() {
    const res = await apiRequest('/api/imagenes');
    if (res.success) {
        const tbody = document.querySelector('#tablaImagenes tbody');
        tbody.innerHTML = '';

        if (res.data.length === 0) {
            tbody.innerHTML = '<tr><td colspan="4">No hay imágenes almacenadas.</td></tr>';
        } else {
            res.data.forEach(img => {
                const tr = document.createElement('tr');
                // Truncate Base64 for display
                const truncatedB64 = img.base64 && img.base64.length > 50
                    ? img.base64.substring(0, 50) + '...'
                    : img.base64;

                tr.innerHTML = `
                            <td>${img.id}</td>
                            <td>${img.nombre || '-'}</td>
                            <td><code style="font-size:11px; color:#555;">${truncatedB64 || '-'}</code></td>
                            <td class="actions">
                                <button class="action-btn edit" onclick='editarImagenUI(${JSON.stringify(img).replace(/'/g, "&#39;")})'>Editar</button>
                                <button class="action-btn del" onclick="eliminarImagen(${img.id})">Borrar</button>
                            </td>
                        `;
                tbody.appendChild(tr);
            });
        }
        document.getElementById('tablaImagenes').classList.add('show');
        document.getElementById('responseImagenes').classList.remove('show');
    } else {
        mostrarRespuestaRapida('responseImagenes', res, true);
    }
}

function editarImagenUI(img) {
    document.getElementById('imgId').value = img.id;
    document.getElementById('imgNombre').value = img.nombre || '';
    document.getElementById('imgBase64').value = img.base64 || '';
    document.getElementById('btnGuardarImagen').innerText = '🔄 Actualizar Imagen';
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

async function guardarImagen() {
    const id = document.getElementById('imgId').value;
    const payload = {
        nombre: document.getElementById('imgNombre').value,
        base64: document.getElementById('imgBase64').value
    };

    const isEdit = id !== '';
    const method = isEdit ? 'PUT' : 'POST';
    const url = isEdit ? `/api/imagenes/${id}` : '/api/imagenes';

    const res = await apiRequest(url, method, payload);
    if (res.success) {
        limpiarFormularioImagen();
        mostrarRespuestaRapida('responseImagenes', res.data, false);
        listarImagenes();
    } else {
        mostrarRespuestaRapida('responseImagenes', res, true);
    }
}

async function eliminarImagen(id) {
    if (!confirm(`¿Deseas ELIMINAR la imagen ID #${id}?`)) return;
    const res = await apiRequest(`/api/imagenes/${id}`, 'DELETE');
    if (res.success) {
        listarImagenes();
        mostrarRespuestaRapida('responseImagenes', { info: 'Imagen Eliminada Exitosamente' }, false);
    } else {
        mostrarRespuestaRapida('responseImagenes', res, true);
    }
}

// Init load
console.log('✅ Panel de Impresora Cargado');