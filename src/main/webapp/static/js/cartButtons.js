main();

function main() {
    document.querySelector(".modal-body").addEventListener("click", (event) => {
        if (event.target.matches(".plus-item")) {
            addOneMoreItemToCart(event.target)
        }
        if (event.target.matches(".minus-item")) {
            removeOneItemFromCart(event.target)
        }
        if (event.target.matches(".fa-trash-o")) {
            removeInstancesOfItemFromCart(event.target)
        }
    });

    function fetchPostMethod(url, content, callback, button, errorCallback) {
        console.log(content);
        fetch(url, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(content)
        })
            .then((resp) => {
                return resp.json()
            })
            .then((data) => callback(data, content))
            .catch(errorCallback);
    }

    function fetchError() {
        console.log("error");
    }

    function rewriteAmountLine(response, data) {
        console.log(response);
        if (response){
            let modal = document.querySelector('.modal-body');
            let itemContainer = modal.querySelector(`[data-product-id="${data.id}"]`);
            let oldAmount = itemContainer.querySelector(".amount").dataset.amount;

            let newAmount = (parseInt(oldAmount) + parseInt(data.amount)).toString();

            itemContainer.querySelector(".amount").dataset.amount = newAmount;
            itemContainer.querySelector(".amount").innerHTML = newAmount;
        }
    }

    function addOneMoreItemToCart(button) {
        let data = {'id': button.dataset.id, 'amount': 1};
        //fetchPostMethod('api/add-to-cart', data, addNewLineToModalBody, fetchError) // original version
        fetchPostMethod('api/add-to-cart', data, rewriteAmountLine, button, fetchError)
    }

    function removeOneItemFromCart(button) {
        let id = Number(button.dataset.id);

        let modal = document.querySelector('.modal-body');
        let itemContainer = modal.querySelector(`[data-product-id="${id}"]`);
        let amount = itemContainer.querySelector(".amount").dataset.amount;
        if (amount <= 1) {
            removeInstancesOfItemFromCart(button);
        } else {
            let data = {'id': id, 'amount': -1};
            //fetchPostMethod('api/add-to-cart', data, addNewLineToModalBody, button, fetchError);
            fetchPostMethod('api/add-to-cart', data, rewriteAmountLine, button, fetchError);
        }
    }
    
    function getUpdatedPriceLine(response, data, button) {
        if (response) {

        }

    }

    function removeInstancesOfItemFromCart(button) {
        let id = Number(button.dataset.id);
        let data = {'id': id};
        fetchPostMethod('api/remove-item', data, removeItemLine, button, fetchError)
        /*
        * Should remove one line from modal body instead of adding one*/
    }

    function emptyCart() {
        fetchPostMethod('api/empty-cart', {'id': 0}, addNewLineToModalBody, fetchError)
    }
}

function removeItemLine(response, data) {
    if (response) {
        let modal = document.querySelector('.modal-body');
        let itemContainer = modal.querySelector(`[data-product-id="${data.id}"]`);
        itemContainer.remove();
        if (document.querySelector("[data-product-id]") == null){
            putEmptyMessage(modal)
        }
    }
}

function putEmptyMessage(target){
    target.insertAdjacentHTML("afterbegin", `<p>Your cart is empty.</p>`);
}
