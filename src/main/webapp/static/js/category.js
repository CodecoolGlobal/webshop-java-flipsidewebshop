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

productsOnlyByCLickedCat();
getProductsFromSideBar();
getDropDownInSideBar();
