const originImg = new Image();
originImg.src = symptomImg.toString();

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

}

async function deleteReserve() {

}