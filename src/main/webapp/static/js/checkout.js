validateCheckout();

function validateCheckout() {
    let placeForNumber = document.querySelector("#number-of-items-in-cart");
    let checkoutButt = document.querySelector(".btn-primary");
    checkoutButt.addEventListener("click", function () {
        if (placeForNumber.dataset.productsNum == 0) {
            alert("Your cart is empty. Please put something into it to checkout.")
        } else {
            askInput(placeForNumber);
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
                <form class="inputs" method="POST" action="/api/reg-order">
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
                </form>
                <a href="/"><input type="submit" value="CONTINUE SHOPPING"></a>
            `
    main.insertAdjacentHTML("beforeend", totalCart);
}
