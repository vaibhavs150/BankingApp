let btn = document.querySelector("#btn");
let colleft = document.querySelector(".column-left");
let colmid = document.querySelector(".column-middle");
let colright = document.querySelector(".column-right");

function myFunction () {
  colleft.classList.toggle("active");
  colmid.classList.toggle("active");
  colright.classList.toggle("active");
};



const optionElement = document.querySelector('#copy-card');
optionElement.addEventListener('click', () => {
  const text = document.querySelector('#card-number').textContent;
  navigator.clipboard.writeText(text);

  alert('Copied to clipboard!');
});

const optionElement2 = document.querySelector('#online-pass-button');
optionElement2.addEventListener('click',() => {
  alert('You will receive a text message with your password soon.')
})


function toggledeactive() {
  var button = document.getElementById("active-deactive-button");
  var activespan = document.getElementById("activespan");
  var status = document.getElementById("status");
  if (button.classList.contains("active")) {
    button.classList.remove("active");
    button.style.backgroundColor = "#f45050";
    button.innerHTML = "Deactive";
    activespan.style.color = "green";
    activespan.innerHTML = "Active";
    status.style.border = "3px solid green"
  } else {
    button.classList.add("active");
    button.style.backgroundColor = "green";
    button.innerHTML = "Active";
    activespan.style.color = "#f45050";
    activespan.innerHTML = "Deactive";
    status.style.border = "3px solid red"

  }
}

// Function to handle form submission for withdrawing money
document.getElementById('withdrawForm').addEventListener('submit', function(event) {
  event.preventDefault(); // Prevent default form submission behavior

  // Get the amount to withdraw from the form input
  const amount = parseFloat(document.getElementById('money').value);

  // Check if the amount is valid and not negative
  if (!isNaN(amount) && amount > 0) {
    // Update balance
    const currentBalance = parseFloat(document.querySelector('.money').textContent);
    const updatedBalance = currentBalance - amount;

    // Ensure balance doesn't go negative
    if (updatedBalance >= 0) {
      document.querySelector('.money').textContent = updatedBalance.toFixed(2); // Update balance display

      // Add transaction history for withdrawal
      const historyElement = document.querySelector('.history');
      const transactionHTML = `
        <div class="element">
          <p>
            <span class="reason">Withdraw</span><span class="flash"><img src="../resources/files/pics/down.svg" alt="" /></span>
          </p>
          <p>
            <span class="date">${getCurrentDate()}</span><span class="money">${amount.toFixed(2)} <span style="color: red">$</span></span>
          </p>
          <hr />
        </div>
      `;
      historyElement.insertAdjacentHTML('afterbegin', transactionHTML);
    } else {
      alert('Insufficient balance!');
    }
  } else {
    alert('Please enter a valid amount to withdraw.');
  }
});

// Function to handle form submission for depositing money
document.getElementById('depositForm').addEventListener('submit', function(event) {
  event.preventDefault(); // Prevent default form submission behavior

  // Get the amount to deposit from the form input
  const amount = parseFloat(document.getElementById('bill').value);

  // Check if the amount is valid and not negative
  if (!isNaN(amount) && amount > 0) {
    // Update balance
    const currentBalance = parseFloat(document.querySelector('.money').textContent);
    const updatedBalance = currentBalance + amount;
    document.querySelector('.money').textContent = updatedBalance.toFixed(2); // Update balance display

    // Add transaction history for deposit
    const historyElement = document.querySelector('.history');
    const transactionHTML = `
      <div class="element">
        <p>
          <span class="reason">Deposit</span><span class="flash"><img src="../resources/files/pics/up.svg" alt="" /></span>
        </p>
        <p>
          <span class="date">${getCurrentDate()}</span><span class="money">${amount.toFixed(2)} <span style="color: green">$</span></span>
        </p>
        <hr />
      </div>
    `;
    historyElement.insertAdjacentHTML('afterbegin', transactionHTML);
  } else {
    alert('Please enter a valid amount to deposit.');
  }
});

// Function to get the current date in the format dd/mm/yyyy
function getCurrentDate() {
  const today = new Date();
  const day = String(today.getDate()).padStart(2, '0');
  const month = String(today.getMonth() + 1).padStart(2, '0'); // January is 0!
  const year = today.getFullYear();

  return `${day}/${month}/${year}`;
}
