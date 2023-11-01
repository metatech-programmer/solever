const toggleButton = document.querySelector("#toggle-theme");
const iconMoon = document.querySelector("#icon-moon");
const iconSun = document.querySelector("#icon-sun");
const themeLabel = document.querySelector("#theme-label");
const body = document.body;
let isDarkMode = false;


window.addEventListener("scroll", function () {
    var button = document.querySelector(".scroll-btn");
    button.classList.toggle("show", window.scrollY > 100);
});

document.querySelector(".scroll-btn").addEventListener("click", function () {
    window.scrollTo({
        top: 0,
        behavior: "smooth"
    });
});


// Agrega una función para cambiar el tema oscuro
toggleButton.addEventListener("click", function () {
    if (isDarkMode) {
        body.classList.remove("dark-mode");
        document.querySelectorAll("a")[0].classList.remove("text-white");
        document.querySelectorAll("a")[1].classList.remove("text-white");
        document.querySelectorAll("a")[2].classList.remove("text-white");
        document.querySelectorAll("a")[3].classList.remove("text-white");
        document.querySelector("span.idioma").classList.add("text-dark");
        iconMoon.style.display = "inline-block";
        iconSun.style.display = "none";
        isDarkMode = false;
        guardarPreferencias();
    } else {
        body.classList.add("dark-mode");
        document.querySelectorAll("a").forEach((a) => {
            a.classList.add("text-white");
        });
        document.querySelector("span.idioma").classList.remove("text-dark");
        iconMoon.style.display = "none";
        iconSun.style.display = "inline-block";
        isDarkMode = true;
        guardarPreferencias();
    }
});

// Agrega una función para guardar la preferencia del usuario en cookies
function guardarPreferencias() {
    const isDarkMode = body.classList.contains("dark-mode");
    document.cookie = `isDarkMode=${isDarkMode}; expires=${new Date(
            2030,
            0,
            1
            ).toUTCString()} path=/`;
}

// Agrega código para cargar las preferencias del usuario al cargar la página
window.addEventListener("load", function () {
    const cookies = document.cookie.split(";");
    cookies.forEach((cookie) => {
        const [name, value] = cookie.split("=");
        if (name.trim() === "isDarkMode") {
            if (value === "true") {
                toggleButton.click();
            }
        }
    });
});


