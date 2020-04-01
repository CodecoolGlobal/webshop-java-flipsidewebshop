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

function addOneItemToCart(addButton) {
    addButton.addEventListener("click", function () {
        let id = addButton.dataset.id;
        let data = {'id': id, 'amount': 1};
        fetchPostMethod(`/api/add-to-cart`, data, addNewLineToModalBody, fetchError)
    })
}
