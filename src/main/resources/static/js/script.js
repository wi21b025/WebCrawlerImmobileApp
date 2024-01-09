document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('calculate-price').addEventListener('click', function() {
        var totalPrice = parseFloat(document.getElementById('total-price').value);
        var area = parseFloat(document.getElementById('area').value);

        if (!isNaN(totalPrice) && !isNaN(area) && area > 0) {
            var pricePerSquareMeter = totalPrice / area;
            document.getElementById('price-per-square-meter').value = pricePerSquareMeter.toFixed(2);
        } else {
            alert('Please enter valid numbers for total price and area.');
        }
    });
});


function toggleForm(formType)
{
    if (formType === 'login')
    {
        document.getElementById('loginForm').style.display = 'block';
        document.getElementById('signupForm').style.display = 'none';
        document.getElementById('loginBtn').classList.replace('btn-secondary', 'btn-primary');
        document.getElementById('signupBtn').classList.replace('btn-primary', 'btn-secondary');
    }
    else
    {
        document.getElementById('loginForm').style.display = 'none';
        document.getElementById('signupForm').style.display = 'block';
        document.getElementById('signupBtn').classList.replace('btn-secondary', 'btn-primary');
        document.getElementById('loginBtn').classList.replace('btn-primary', 'btn-secondary');
    }
}
