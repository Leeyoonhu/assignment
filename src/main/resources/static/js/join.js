const userNameInput = document.getElementById('userName');
const userIdInput = document.getElementById('email');
const passwordInput = document.getElementById('password');
const form = document.getElementById('joinForm');

let check = false;
let check1 = false;
let check2 = false;

userIdInput.addEventListener("focusout", async function(){
    let url = "/api/v1/users/join/checkUserId"
    let option = {
        method : "post",
        body: userIdInput.textContent
    }
    try {
        let res = await fetch(url, option)
        let result = await res.json();
        if(result === "존재함"){
            alert("이미 존재하는 아이디입니다.");
            userIdInput.textContent = "";
        } else {
            alert("사용 가능한 아이디입니다.")
            window.location.href = "/";
        }
    } catch(err) {
        console.log(err);
    }
})
