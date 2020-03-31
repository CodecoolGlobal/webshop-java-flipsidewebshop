function getAllProducts() {
    let seeAll = document.querySelector('.all-product');
    let containers = document.querySelectorAll('.container');
    let suppliers = document.querySelectorAll('.supplier');
    seeAll.addEventListener('click', function () {
        for (let con of containers) {
            con.classList.remove('hidden');
        }
        for (let supp of suppliers) {
            supp.classList.remove('hidden');
        }
    })
}

function openNav() {
    document.getElementById("mySidebar").style.width = "200px";
}

function closeNav() {
    document.getElementById("mySidebar").style.width = "0";
}

getAllProducts();

