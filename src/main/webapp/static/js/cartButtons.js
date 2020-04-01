main();

function main() {
    let addButtons = document.querySelectorAll(".plus-item");
    for (let addButton of addButtons) {
        console.log("plus");
        addOneMoreItemToCart(addButton);
    }

    let removeButtons = document.querySelectorAll(".minus-item");
    for (let removeButton of removeButtons) {
        console.log("minus");
        removeOneItemFromCart(removeButton);
    }
}


function fetchPostMethod(url, content, callback, errorCallback) {
    console.log(content);
    fetch(url, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(content)
    })
        .then((resp) => {return resp.json()})
        .then((data) => callback(data, content))
        .catch(errorCallback);
}

function addNewLineToModalBody(response, data) {
    let modal = document.querySelector('.modal-body');
    console.log(response);
    modal.innerHTML += `
<div>
    We have of item with id ${data.id}, added amount of ${data.amount} and the response is ${response}. 
</div>`
}

function fetchError() {
    console.log("error");
}

function addOneMoreItemToCart(button) {
    button.addEventListener("click", function () {
        let id = Number(button.dataset.id);
        let amount = Number(button.dataset.amount);
        let data = {'id': id, 'amount': 1};
        console.log(`id: ${data.id}, amount: ${data.amount}`);
        fetchPostMethod('api/add-to-cart', data, addNewLineToModalBody, fetchError)
    })
}

function removeOneItemFromCart(button) {
    button.addEventListener("click", function () {
        let id = Number(button.dataset.id);
        let amount = Number(button.dataset.amount);
        let data = {'id': id, 'amount': -1};
        console.log(`id: ${data.id}, amount: ${data.amount}`);
        fetchPostMethod('api/remove-item', data, addNewLineToModalBody, fetchError)
    })
}

function emptyCart(emptyButton) {
    emptyButton.addEventListener("click", function () {
        fetchPostMethod('api/empty-cart', {'id': 0}, addNewLineToModalBody, fetchError)
    })
}