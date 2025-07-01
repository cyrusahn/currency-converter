document.addEventListener("DOMContentLoaded", () => {
    const inputs = document.querySelectorAll(".currency-input");

    inputs.forEach(input => {
        input.addEventListener("input", () => {
            const fromCurrency = input.id;
            const amount = input.value;
            console.log(`Input changed: ${fromCurrency} = ${amount}`);

            if (amount === "") return;

            fetch(`/api/convert?from=${fromCurrency}&amount=${amount}`)
                .then(res => res.json())
                .then(data => {
                    for (const [currency, value] of Object.entries(data)) {
                        const targetInput = document.getElementById(currency);
                        if (targetInput && currency !== fromCurrency) {
                            targetInput.value = value.toFixed(2);
                        }
                    }
                });
        });
    });
});
