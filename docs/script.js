// Smooth scrolling for navigation links
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        const href = this.getAttribute('href');
        if (href !== '#' && document.querySelector(href)) {
            e.preventDefault();
            document.querySelector(href).scrollIntoView({
                behavior: 'smooth',
                block: 'start'
            });
        }
    });
});

// Active navigation link highlighting
window.addEventListener('scroll', () => {
    const sections = document.querySelectorAll('section[id]');
    const navLinks = document.querySelectorAll('.nav-links a');

    sections.forEach(section => {
        const sectionTop = section.offsetTop - 100;
        const sectionBottom = sectionTop + section.offsetHeight;
        const scrollPosition = window.scrollY + 200;

        if (scrollPosition >= sectionTop && scrollPosition <= sectionBottom) {
            navLinks.forEach(link => {
                link.classList.remove('active');
            });
            const activeLink = document.querySelector(`.nav-links a[href="#${section.id}"]`);
            if (activeLink) {
                activeLink.style.color = 'var(--primary-color)';
            }
        }
    });
});

// Copy to clipboard functionality for code blocks
const preElements = document.querySelectorAll('pre');
preElements.forEach(pre => {
    const code = pre.querySelector('code');
    if (code) {
        const button = document.createElement('button');
        button.textContent = 'Copiar';
        button.className = 'copy-btn';
        button.style.cssText = `
            position: absolute;
            top: 10px;
            right: 10px;
            padding: 5px 10px;
            background-color: var(--primary-color);
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 0.85rem;
            transition: var(--transition);
        `;

        button.addEventListener('mouseover', () => {
            button.style.backgroundColor = 'var(--success-color)';
        });

        button.addEventListener('mouseout', () => {
            button.style.backgroundColor = 'var(--primary-color)';
        });

        button.addEventListener('click', () => {
            const text = code.textContent;
            navigator.clipboard.writeText(text).then(() => {
                const originalText = button.textContent;
                button.textContent = '¡Copiado!';
                button.style.backgroundColor = 'var(--success-color)';
                setTimeout(() => {
                    button.textContent = originalText;
                    button.style.backgroundColor = 'var(--primary-color)';
                }, 2000);
            }).catch(err => {
                console.error('Error al copiar:', err);
            });
        });

        pre.style.position = 'relative';
        pre.appendChild(button);
    }
});

// Animate elements on scroll
const observerOptions = {
    threshold: 0.1,
    rootMargin: '0px 0px -50px 0px'
};

const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            entry.target.style.opacity = '1';
            entry.target.style.transform = 'translateY(0)';
        }
    });
}, observerOptions);

// Apply animation to cards
document.querySelectorAll('.tech-card, .step, .doc-card, .features-list li').forEach(element => {
    element.style.opacity = '0';
    element.style.transform = 'translateY(20px)';
    element.style.transition = 'opacity 0.6s ease-out, transform 0.6s ease-out';
    observer.observe(element);
});

// Mobile menu toggle
document.addEventListener('DOMContentLoaded', () => {
    const navbar = document.querySelector('.navbar');
    const navLinks = document.querySelector('.nav-links');

    // Optional: Add mobile menu button if needed
    if (window.innerWidth < 768) {
        const toggleButton = document.createElement('button');
        toggleButton.textContent = '☰';
        toggleButton.style.cssText = `
            display: none;
            background: none;
            border: none;
            color: white;
            font-size: 1.5rem;
            cursor: pointer;
        `;

        toggleButton.addEventListener('click', () => {
            navLinks.style.display = navLinks.style.display === 'flex' ? 'none' : 'flex';
        });
    }
});

// Log para debugging
console.log('Sistema de Impresora - GitHub Pages Loaded');
console.log('Documentación disponible en: http://localhost:8080/swagger-ui.html');
