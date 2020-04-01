main();

function main() {
    let cartButtons = document.querySelectorAll(".cart-button");
    for (let cartButton of cartButtons) {
        cartButton.addEventListener("click", function () {
            let id = cartButton.dataset.id;
            fetchResultsPostMethod(id, addToModalBody, errorProne)

        })
    }
}


function fetchResultsPostMethod(content, callback, errorCallback) {
    console.log(content);
    let data = {
        'id': content,
    };
    fetch(`/api/add-to-cart`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    })
        .then((resp) => {return resp.json()})
        .then((data) => callback(data))
        .catch(errorCallback);
}

function addToModalBody(data) {
    let modal = document.querySelector('.modal-body');
    console.log(data);
    modal.innerHTML += `
<div>
    We have of item with id ${data}. 
</div>`
}

function errorProne() {
    console.log("error");
}