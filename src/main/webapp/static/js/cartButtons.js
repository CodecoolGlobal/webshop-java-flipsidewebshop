main();

function main() {
    document.querySelector(".modal-body").addEventListener("click", (event) => {
        if (event.target.matches(".plus-item")) {
            addOneMoreItemToCart(event.target)
        }
        if (event.target.matches(".minus-item")) {
            removeOneItemFromCart(event.target)
        }
        if (event.target.matches(".delete-item")) {
            removeAllInstancesOfItemFromCart(event.target)
        }
    });

    /*
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

     */


    function fetchPostMethod(url, content, callback, errorCallback) {
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

    function addOneMoreItemToCart(button) {
        let id = Number(button.dataset.id);
        let data = {'id': id, 'amount': 1};
        fetchPostMethod('api/add-to-cart', data, addNewLineToModalBody, fetchError)

    }

    function removeOneItemFromCart(button) {
        let id = Number(button.dataset.id);
        let data = {'id': id, 'amount': -1};
        fetchPostMethod('api/add-to-cart', data, addNewLineToModalBody, fetchError)
    }

    function removeAllInstancesOfItemFromCart(button) {
        let id = Number(button.dataset.id);
        let data = {'id': id};
        fetchPostMethod('api/remove-item', data, addNewLineToModalBody, fetchError)
    }

    function emptyCart() {
        fetchPostMethod('api/empty-cart', {'id': 0}, addNewLineToModalBody, fetchError)
    }
}