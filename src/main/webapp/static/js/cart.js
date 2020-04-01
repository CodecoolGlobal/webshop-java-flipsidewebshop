main();

function main() {
    let cartButtons = document.querySelectorAll(".cart-button");
    for (let cartButton of cartButtons) {
        cartButton.addEventListener("click", function () {
            let id = cartButton.dataset.id;
            fetchResultsPostMethod(id, (response)=>{console.log("callback finished, result: " + response)})

        })

    };

    let modal = document.querySelector('.modal-body');

}


function fetchResultsPostMethod(content, callback) {
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
        .then((data) => callback(data));
}

function addToModalBody() {}