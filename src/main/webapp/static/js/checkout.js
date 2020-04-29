validateCheckout();

function validateCheckout() {
    let placeForNumber = document.querySelector("#number-of-items-in-cart");
    let checkoutButt = document.querySelector(".btn-primary");
    checkoutButt.addEventListener("click", function () {
        if (placeForNumber.dataset.productsNum == 0) {
            alert("Your cart is empty. Please put something into it to checkout.")
        } else {
            askInput(placeForNumber);
            submitButtonListener();
        }
    })
}

function askInput(placeForNumber) {
    let main = document.querySelector('.main');
    main.innerHTML = ``;
    let totalCart = `
            <div>
                <p>You have ${placeForNumber.dataset.productsNum} item in your cart</p>
                <p>Total price: </p>
            </div>
            <div class="questions">
                <div class="inputs">
                <div>
                    <label for="name">Name: </label>
                    <input type="text" name="name" required>
                </div>
                <div>
                    <label for="email">Email: </label>
                    <input type="email" name="email" required>
                </div>
                <div>
                    <label for="phone">Phone number: </label>
                    <input type="number" name="phone" required>
                </div>
                <div>
                    <label for="baddress">Billing Address: </label>
                    <input type="text" name="baddress" required>
                </div>
                <div>
                    <label for="shipaddress">Shipping Address: </label>
                    <input type="text" name="shipaddress" required>
                </div>
                <div>    
                    <input type="submit" value="PAYMENT">
                </div>
                </div>
            `
    main.insertAdjacentHTML("beforeend", totalCart);
}

function submitButtonListener(){
    document.querySelector("[type='submit']").addEventListener("click", () => {
        let data = {
            name: document.querySelector(`[name="name"]`),
            email: document.querySelector(`[name="email"]`),
            phone: document.querySelector(`[name="phone"]`),
            baddress: document.querySelector(`[name="baddress"]`),
            shipaddress: document.querySelector(`[name="shipaddress"]`)
        };
        fetchOrder(data, thanksForOrder);
    })
}

function fetchOrder(content, callback) {
    fetch(`/api/reg-order`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(content)
    })
    .then(resp => resp.json())
    .then(json_response => {
        callback(json_response)
    })
}

function thanksForOrder(boolean){
    if (boolean) {
        let main = document.querySelector('.main');
        main.innerHTML = `
        <div class="centered">
        <h1>Thank you, your order is processed.</h1>
        <a href="/"><button class="btn btn-primary">Back to Main Page</button></a>
        </div>
        `;
    }

}

