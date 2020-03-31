function productsOnlyByCLickedCat() {
    let containers = document.querySelectorAll('.supplier');
    let cards = document.querySelectorAll('.card-sup');
    for (let container of containers) {
        let containerId = container.dataset.supid;
        for (let card of cards) {
            let cardId = card.dataset.sid;
            if (cardId != containerId) {
                card.addEventListener('click', function () {
                        container.classList.add('hidden');
                    }
                )
            } else {
                container.classList.remove('hidden');
            }
        }

    }
}

function getProductsFromSideBar() {
    let suppliers = document.querySelectorAll('.suppliers');
    let containers = document.querySelectorAll('.supplier');
    let categories = document.querySelectorAll('.container');
    for (let supplier of suppliers) {
        let suId = supplier.dataset.suid;
        for (let container of containers) {
            let conId = container.dataset.supid;
            for (let cat of categories) {
                supplier.addEventListener('click', function () {
                        cat.classList.remove('hidden');
                        if (suId != conId) {
                            container.classList.add('hidden');
                        } else {
                            container.classList.remove('hidden');
                        }
                    }
                )}
        }
    }
}

function getDropDownInSideBar() {
    let dropdown = document.querySelector('.dropdown');
    dropdown.addEventListener("click", function () {
        this.classList.toggle("active");
        let dropdownContent = document.querySelector('.supplier-sidebar');
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
