

console.log("script.js loaded!");

const products = {
    1: { name: "iPhone 17", type: "Regular", color: "Blue" },
    2: { name: "Headphones", type: "AirPods Max 2", color: "Blue" },
    3: { name: "Keyboard", type: "KiiBOOM Phantom 98", color: "Pink" }
};

async function getOrCreateCarrito() {
    console.log("getOrCreateCarrito called");
    let carritoId = localStorage.getItem("carritoId");
    console.log("Existing cart ID:", carritoId);

    if (!carritoId) {
        console.log("Creating new carrito...");
        try {
            const res = await fetch("http://localhost:8080/api/carritos", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    idUsuario: "guest",
                    correoUsuario: "guest@example.com"
                })
            });

            console.log("Create cart STATUS:", res.status);

            if (!res.ok) {
                const errorText = await res.text();
                console.error("FAILED TO CREATE CART:", errorText);
                return null;
            }

            const carrito = await res.json();
            console.log("Created cart:", carrito);

            if (carrito && carrito.idCarrito) {
                carritoId = carrito.idCarrito;
                localStorage.setItem("carritoId", carritoId);
                console.log("Cart ID saved:", carritoId);
            }
        } catch (err) {
            console.error("Error creating cart:", err);
            return null;
        }
    }

    return carritoId;
}

async function addToCart(articuloId, precio, cantidad = 1) {
    console.log("addToCart called!", articuloId, precio, cantidad);

    const carritoId = await getOrCreateCarrito();

    if (!carritoId) return;

    try {
        const res = await fetch(
            `http://localhost:8080/api/carritos/${carritoId}/lineas/${articuloId}`,
            {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    precioUnitario: precio,
                    numeroUnidades: cantidad
                })
            }
        );

        if (!res.ok) {
            console.error(await res.text());
            return;
        }

        console.log("Item added!");
    } catch (err) {
        console.error(err);
    }

    loadCart();
}


async function loadCart() {
    const tableBody = document.getElementById("cart-items");

    if (!tableBody) {
        console.log("No cart table found on this page");
        return;
    }

    const carritoId = localStorage.getItem("carritoId");

    if (!carritoId) {
        tableBody.innerHTML = "<tr><td colspan='4'>Your cart is empty</td></tr>";
        return;
    }

    const res = await fetch(`http://localhost:8080/api/carritos/${carritoId}`);
    if (!res.ok) {
        console.error("Failed to load cart:", res.status);
        return;
    }

    const carrito = await res.json();
    console.log("CARRITO RESPONSE:", carrito);

    tableBody.innerHTML = "";

    carrito.carritoLineas.forEach(linea => {
    const product = products[linea.idArticulo];

    const row = `
        <tr>
            <td>${product ? product.name : "Unknown product"}</td>
            <td>${product ? product.type : "-"}</td>
            <td>${product ? product.color : "-"}</td>
            <td>${linea.costaLinea} €</td>
             <td>${linea.numeroUnidades}</td> 
              <td>
            <button onclick="deleteItem(${linea.idCarritoLinea})">
                Delete
            </button>
        </td>
        </tr>
    `;
    tableBody.innerHTML += row;
    const totalElement = document.getElementById("cart-total");

if (totalElement) {
    totalElement.innerHTML = `${carrito.precioTotal || 0} €`;
}
});
 
}

async function addToCartFromUI(articuloId, precio, amountInputId) {
    console.log("Reading UI values...");

    const amountInput = document.getElementById(amountInputId);
    let amount = parseInt(amountInput.value);

    
    amount = Math.max(1, amount || 1);

    console.log("Amount:", amount);

    await addToCart(articuloId, precio, amount);

    
}

async function deleteItem(lineaId) {
    const carritoId = localStorage.getItem("carritoId");

    if (!carritoId) {
        console.error("No cart ID found");
        return;
    }

    try {
        const res = await fetch(
            `http://localhost:8080/api/carritos/${carritoId}/linea/${lineaId}`,
            {
                method: "DELETE"
            }
        );

        console.log("Delete status:", res.status);

        if (!res.ok) {
            console.error("Failed to delete item");
            return;
        }

    } catch (err) {
        console.error("Error deleting item:", err);
    }

    
    loadCart();
}


async function feedbackButton() {
    const name = document.getElementById("name_id").value.trim();
    const phone = document.getElementById("phone_id").value.trim();
    const email = document.getElementById("email_id").value.trim();
    const address = document.getElementById("address").value.trim();
    
    
    const carritoId = localStorage.getItem("carritoId");
    if (!carritoId) {
        alert("Your cart is empty! Add some items first.");
        return;
    }
    
    
    if (!name || !phone || !email || !address) {
        alert("Please fill in all fields correctly before purchasing!");
        return;
    }
    
    
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        alert("Please enter a valid email address!");
        return;
    }
    

    const phoneDigits = phone.replace(/\D/g, '');
    if (phoneDigits.length < 7) {
        alert("Please enter a valid phone number!");
        return;
    }
    
    alert("Thank you for your purchase!");
}

window.onload = loadCart;