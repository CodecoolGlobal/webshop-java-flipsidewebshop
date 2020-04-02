main();

function main() {
    let title = document.querySelector('.modal-title');
    title.innerHTML = ``;
    title.insertAdjacentHTML("beforeend", "Shopping Cart");
    let cartButtons = document.querySelectorAll(".cart-button");
    for (let cartButton of cartButtons) {
        addOneItemToCart(cartButton);
    }
}


function fetchPostMethod(url, content, callback, errorCallback) {
    fetch(url, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({'id': content.id, 'amount': content.amount })
    })
        .then((resp) => {return resp.json()})
        .then((data) => callback(data, content))
        .catch(errorCallback);
}

function recalcNumberOfItemsInCart(data) {
    let placeForNumber = document.querySelector("#number-of-items-in-cart");
    let currentNumberOfItemsInCart = Number(placeForNumber.dataset.productsNum);
    placeForNumber.textContent = ` ${currentNumberOfItemsInCart + data.amount}`;
    placeForNumber.dataset.productsNum = `${currentNumberOfItemsInCart + data.amount}`;
}

function addNewLineToModalBody(response, data) {
    if (response){
        let modal = document.querySelector('.modal-body');
        if (modal.querySelector("p")){
            modal.querySelector("p").remove()
        }
        let itemElement = modal.querySelector(`[data-product-id="${data.id}"]`);
        if (itemElement == null){
            modal.insertAdjacentHTML("beforeend", template.modal(data))
        } else {
            addNewAmountToExisting(itemElement, data.amount)
        }
        recalcNumberOfItemsInCart(data);
    }
}


function recalcSubtotal() {
    let modal = document.querySelector('.modal-body');
    let subtotalLine = modal.querySelector('.subtotal');
    let subtotal = Number(modal.dataset.amount) * Number(modal.dataset.price);
    subtotalLine.textContent = `Subtotal: ${subtotal} USD`;
}

function addNewAmountToExisting(target, addedAmount){
    let modal = document.querySelector('.modal-body');
    let amountField = target.querySelector(".amount");
    let oldAmount = amountField.dataset.amount;
    amountField.dataset.amount = (parseInt(oldAmount) + parseInt(addedAmount)).toString();
    amountField.innerHTML = amountField.dataset.amount;
    modal.dataset.amount = (parseInt(oldAmount) + parseInt(addedAmount)).toString();
    recalcSubtotal();
}

function fetchError() {
    console.log("error");
}

function gatherData(container){
    return {
        "id": container.querySelector("[data-id]").dataset.id,
        "price": container.querySelector("[data-price]").dataset.price,
        "description": container.querySelector(".description").dataset.description,
        "name": container.querySelector(".card-title").dataset.name
    };
}


function addOneItemToCart(addButton) {
    addButton.addEventListener("click", (event) => {
        //let id = Number(addButton.dataset.id);
        //let data = {'id': id, 'amount': 1};
        let data = gatherData(event.target.closest(".product"));
        data.amount = 1;
        fetchPostMethod(`/api/add-to-cart`, data, addNewLineToModalBody, fetchError)
    })
}

let template = {
    modal: (product) => {
        return `
        <div class="itemwrapper" data-product-id="${product.id}" data-price="${product.price.slice(0, -4)}" data-amount="${product.amount}">
            <div class="picture">
                <img class="pic"
                     src='/static/img/product_${product.id}.jpg' alt=""/>
            </div>
            <div class="infoaboutitem">
                <div>${product.name}</div>
                <div data-price="${product.price}">Unit Price: ${product.price}</div>
                <div class="amount-button">
                    <button name="minusitem" class="minus-item item-control-button" data-id="${product.id}" data-amount="${product.amount}">-</button>
                    <div class="amount" data-amount="${product.amount}">${product.amount}</div>
                    <button name="plusitem" class="plus-item item-control-button"  data-id="${product.id}" data-amount="${product.amount}">+</button>
                </div>
                <div>
                    <div class="subtotal" data-subtotal="${Math.round(product.amount * product.price.slice(0,-4) *100) /100}">Subtotal: ${Math.round(product.amount * product.price.slice(0,-4) *100) /100} USD</div>
                    <button name="deleteitem" id="deletebutton" class="fa fa-trash-o" data-id="${product.id}"></button>
                </div>
            </div>
        </div>
        
        `
    },
}

function displayNumberOfItems(number) {
    let placeForNumber = document.querySelector("#");
}
