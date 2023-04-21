const name = document.getElementById('userName');
const id = document.getElementById('userId');
const password = document.getElementById('password');
const form = document.getElementById('joinForm');


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
    try {
        fetch(url, option)
            .then((response) => {
                if (response.ok) {
                    alert("사용 가능한 아이디입니다.")
                } else {
                    duplicateId()
                }
            })
    } catch (err) {
        console.log(err)
    }
})


