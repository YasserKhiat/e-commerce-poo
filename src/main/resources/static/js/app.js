document.addEventListener('DOMContentLoaded', function () {
    const revealItems = document.querySelectorAll('[data-reveal]');
    if (revealItems.length) {
        const observer = new IntersectionObserver(
            (entries) => {
                entries.forEach((entry) => {
                    if (entry.isIntersecting) {
                        entry.target.classList.add('is-in');
                        observer.unobserve(entry.target);
                    }
                });
            },
            { threshold: 0.12 }
        );

        revealItems.forEach((item) => observer.observe(item));
    }

    const nav = document.querySelector('.premium-navbar');
    if (nav) {
        const onScroll = () => {
            if (window.scrollY > 8) {
                nav.classList.add('shadow');
            } else {
                nav.classList.remove('shadow');
            }
        };
        onScroll();
        window.addEventListener('scroll', onScroll, { passive: true });
    }
});
