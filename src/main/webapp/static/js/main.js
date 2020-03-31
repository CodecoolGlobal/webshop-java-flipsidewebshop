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
                )
            }
        }

    }
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