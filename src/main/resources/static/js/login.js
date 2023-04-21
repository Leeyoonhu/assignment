const id = document.getElementById('userId');
const password = document.getElementById('password');

async function login() {
    if(id.value === "" || password.value === "") {
        alert("회원 정보를 입력하세요")
    } else {
        let url = "/api/v1/users/login"
        let formData = new FormData();
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
                        location.href="/"
                    } else {
                        alert("존재하지 않는 회원입니다.")
                        id.value = ""
                        password.value = ""
                    }
                })
        } catch (err) {
            console.log(err)
        }
    }
}