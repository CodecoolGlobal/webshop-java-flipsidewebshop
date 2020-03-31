function productsOnlyByCLickedCat() {
    let containers = document.querySelectorAll('.container');
    let cards = document.querySelectorAll('.card');
    for (let container of containers) {
        let containerId = container.dataset.catid;
        for (let card of cards) {
            let cardId = card.dataset.id;
            if (cardId != containerId) {
                card.addEventListener('click', function () {
                        container.classList.add('hidden');
                    }
                )} else {
                container.classList.remove('hidden');
            }
        }

    }
}

function getProductsFromSideBar() {
    let categories = document.querySelectorAll('.categories');
    let containers = document.querySelectorAll('.container');
    let suppliers = document.querySelectorAll('.supplier');
    for (let category of categories) {
        let catId = category.dataset.catid;
        for (let container of containers) {
            let conId = container.dataset.catid;
            for (let supp of suppliers) {
                category.addEventListener('click', function () {
                    supp.classList.remove('hidden');
                    if (catId != conId) {
                        container.classList.add('hidden');
                    } else {
                        container.classList.remove('hidden');
                    }
                })
            }

        }
    }
}

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

function getDropDownInSideBar() {
    let dropdown = document.querySelector('.dropdown-btn');
        dropdown.addEventListener("click", function() {
            this.classList.toggle("active");
            let dropdownContent = document.querySelector('.category-sidebar');
            if (dropdownContent.style.display === "block") {
                dropdownContent.style.display = "none";
            } else {
                dropdownContent.style.display = "block";
            }
        });
}

function openNav() {
    document.getElementById("mySidebar").style.width = "200px";
    document.getElementById("main").style.marginLeft = "200px";
}

/* Set the width of the sidebar to 0 and the left margin of the page content to 0 */
function closeNav() {
    document.getElementById("mySidebar").style.width = "0";
    document.getElementById("main").style.marginLeft = "0";
}

productsOnlyByCLickedCat();
getProductsFromSideBar();
getAllProducts();
getDropDownInSideBar();
