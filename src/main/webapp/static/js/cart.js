main();

function main() {
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
    let modal = document.querySelector('.modal-body');
    console.log(response);
    if (response){
        modal.innerHTML += `
            <div>
                We have of item with id ${data.id}, added amount of ${data.amount}
                and the response is ${response}. 
            </div>`;
        modal.insertAdjacentHTML("beforeend", template.modal(data))
    }
}

function fetchError() {
    console.log("error");
}

function gatherData(target){
    console.log(target.dataset.id);
    let storedData = {
        "id" : target.dataset.id,
        "price" : target.parentNode.querySelector(".price").dataset.price,
        "description" :  target.closest(".product").querySelector(".description").dataset.description,
        "name":target.closest(".product").querySelector(".card-title").dataset.name
    };
    return storedData;
}


function addOneItemToCart(addButton) {
    addButton.addEventListener("click", (event) => {
        //let id = Number(addButton.dataset.id);
        //let data = {'id': id, 'amount': 1};
        let data = gatherData(event.target.parentElement);
        data.amount = 1;
        fetchPostMethod(`/api/add-to-cart`, data, addNewLineToModalBody, fetchError)
    })
}

let template = {
    modal: (product) => {
        return `
        <div class="itemwrapper" style="display:inline-flex">
            <div class="picture" style="width:200px;height:200px">
                <img class=""
                     src='/static/img/product_${product.id}.jpg' alt=""/>
            </div>
            <div class="infoaboutitem" style="display:flex;flex-direction: column">
                <div>${product.name}</div>
                <div>${product.price}">Price</div>
                <div>
                    <button name="minusitem" class="minus-item" data-id="${product.id}">Minus</button>
                    <div class="amount">${product.amount}</div>
                    <button name="plusitem" class="plus-item"  data-id="${product.id}">Plus</button>
                    <button name="deleteitem" class="del-item" data-id="${product.id}">Del</button>
                </div>
            </div>
        </div>
        
        `
    },
}
