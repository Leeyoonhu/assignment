const input = document.getElementById("image-upload");

let img = new Image();
let canvas = document.createElement("canvas");
let ctx = canvas.getContext("2d");
let MAX_WIDTH = 320;
let thumbnailDiv = document.getElementById("thumbnail");

function validateFileExtension() {
    let allowedExtensions = /(\.jpg|\.jpeg|\.png|\.gif)$/i;

    if (!allowedExtensions.exec(input.value)) {
        console.log("이미지가 아닌 파일이 업로드되었음");
        alert('이미지 파일만 업로드 가능합니다.');
        input.value = '';
        return false;
    }
}

function createThumbnail() {
    const reader = new FileReader();
    reader.onload = function (event) {
        img.onload = function() {
            let width = img.width;
            let height = img.height;

            if (width > MAX_WIDTH) {
                height *= MAX_WIDTH / width;
                width = MAX_WIDTH;
            }

            canvas.width = width;
            canvas.height = height;
            ctx.drawImage(img, 0, 0, width, height);

            thumbnailDiv.innerHTML = "";
            thumbnailDiv.appendChild(canvas);
        };
        img.src = event.target.result;
    }
    reader.readAsDataURL(input.files[0]);

    const thumbnailDiv = document.getElementById("thumbnail");
    thumbnailDiv.innerHTML = "";
    thumbnailDiv.appendChild(img);
}

input.addEventListener("change", createThumbnail);