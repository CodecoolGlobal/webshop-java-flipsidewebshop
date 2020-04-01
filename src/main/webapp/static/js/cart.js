main();

function main() {
    let cartButtons = document.querySelectorAll(".cart-button");
    for (let cartButton of cartButtons) {
        cartButton.addEventListener("click", function () {
            let id = cartButton.dataset.id;
            let idMap = {"id": id};
            fetchResultsPostMethod(idMap, addToModalBody());
        })

    }
}


function fetchResultsPostMethod(content, callback) {
    fetch(`/api/add-to-cart`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(content)
    })
        .then((resp) => {return resp.json()})
        .then((data) => callback(data));
}

function addToModalBody(data) {
    let modal = document.querySelector('.modal-body');
    console.log(data);
    modal.innerHTML += `
<div>
    We have of item with id ${data}. 
</div>`
}