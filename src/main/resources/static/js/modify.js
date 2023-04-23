const originImg = new Image();
originImg.src = symptomImg.toString();

let phoneNo, symptom, date;

originImg.onload = function() {
    let width = originImg.width;
    let height = originImg.height;

    if (width > MAX_WIDTH) {
        height *= MAX_WIDTH / width;
        width = MAX_WIDTH;
    }

    canvas.width = width;
    canvas.height = height;
    ctx.drawImage(originImg, 0, 0, width, height);

    thumbnailDiv.innerHTML = "";
    thumbnailDiv.appendChild(canvas);
};


async function saveReserve() {
    let url = "/api/v1/reserve/save"
    let form = new FormData();
    phoneNo = document.getElementById("phoneNo").value;
    symptom = document.getElementById("symptom").value;
    date = document.getElementById("datepicker").value;

    form.append("yadmNm", yadmNm);
    form.append("id", id);
    form.append("phoneNo", phoneNo);
    form.append("symptom", symptom);
    form.append("date", date);
    if(input.files[0] !== undefined) {
        form.append("uploadFile", imageFile, imageFile.name);
    }
    let option = {
        method: "post",
        body: form
    }

    if(input.files[0] !== undefined && phoneNo && symptom && date) {
        fetchSave(url, option);

    } else if (phoneNo && symptom && date) {
        if (confirm("증상 이미지가 변경되지 않았습니다. 이대로 진행 할까요?")) {
            fetchSave(url, option);
        }
    } else {
        alert("진료 신청란을 작성해주세요.")
    }

}


function fetchSave(url, option) {
    fetch(url, option)
        .then(response => {
            if (response.ok) {
                alert("진료 수정이 완료되었습니다.")
                window.location.href = "/"
            } else if(response.status === 500) {
                alert("진료 수정에 실패하였습니다. 다음에 다시 시도해주세요.")
            }
        })
        .then(data => {
            console.log(data);
        })
        .catch(error => {
            console.error('fetch 함수 에러 코드 :', error);
        });
}

async function deleteReserve() {
    let url = "/api/vi/reserve/delete"
}

