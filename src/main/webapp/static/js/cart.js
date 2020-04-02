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
    console.log(content);

    fetch(url, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({'id': content.id, 'amount': content.amount })
    })
        .then((resp) => {return resp.json()})
        .then((data) => callback(data, content))
        .catch(errorCallback);
}

function addNewLineToModalBody(response, data) {
    console.log(response);
    if (response){
        let modal = document.querySelector('.modal-body');
        let itemElement = modal.querySelector(`[data-product-id="${data.id}"]`);
        if (itemElement == null){
            modal.insertAdjacentHTML("beforeend", template.modal(data))
        } else {
            addNewAmountToExisting(itemElement, data.amount)
        }

    }
}


function addNewAmountToExisting(target, addedAmount){
    let amountField = target.querySelector(".amount");
    let oldAmount = amountField.dataset.amount;
    amountField.dataset.amount = (parseInt(oldAmount) + parseInt(addedAmount)).toString();
    amountField.innerHTML = amountField.dataset.amount;
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
        <div class="itemwrapper" style="display:inline-flex" data-product-id="${product.id}">
            <div class="picture" style="display:inline-block;width:200px;height:200px">
                <img class="pic"
                     src='/static/img/product_${product.id}.jpg' alt=""/>
            </div>
            <div class="infoaboutitem" style="display:flex;flex-direction: column">
                <div>${product.name}</div>
                <div data-price="${product.price}">Unit Price: ${product.price}</div>
                <div>
                    <button name="minusitem" class="minus-item item-control-button" data-id="${product.id}" data-amount="${product.amount}">-</button>
                    <div class="amount" data-amount="${product.amount}">${product.amount}</div>
                    <button name="plusitem" class="plus-item item-control-button"  data-id="${product.id}" data-amount="${product.amount}">+</button>
                </div>
                <div>
                    <button name="deleteitem" class="del-item item-control-button" data-id="${product.id}">Del</button>
                </div>
            </div>
        </div>
        
        `
    },
};
