const name = document.getElementById('userName');
const id = document.getElementById('userId');
const password = document.getElementById('password');
let check = false;

function duplicateId() {
    alert("이미 존재하는 아이디입니다.");
    id.value = "";
}

id.addEventListener("focusout", async function () {
    let url = "/api/v1/users/checkUserId"
    let formData = new FormData();
    formData.append('id', id.value)
    let option = {
        method: "post",
        body: formData
    }
    if(id.value !== "") {
        try {
            fetch(url, option)
                .then((response) => {
                    if (response.ok) {
                        alert("사용 가능한 아이디입니다.")
                        check = true;
                    } else {
                        duplicateId()
                        check = false;
                    }
                })
        } catch (err) {
            console.log(err)
        }
    } else {
        alert("아이디를 입력해주세요.")
    }
})

async function join() {
    if(name.value !== "" && id.value !== "" && password.value !== "" && check === true) {
        let url = "/api/v1/users/join"
        let formData = new FormData();
        formData.append("name", name.value);
        formData.append("id", id.value);
        formData.append("password", password.value);
        let option = {
            method: "post",
            body: formData
        }
        try {
            fetch(url, option)
                .then((response) => {
                    if (response.ok) {
                        alert("회원 가입이 완료되었습니다.")
                        location.href="/"
                    } else {
                        alert("이미 존재하는 회원입니다.")
                        name.value = ""
                        id.value = ""
                        password.value = ""
                    }
                })
        } catch (err) {
            console.log(err)
        }
    } else {
        alert("회원 정보를 입력해주세요")
    }
}

