document.addEventListener("DOMContentLoaded", callHospInfo);

const apiKey = '4jFXJ6t7FNyEQ5xKvZ6OQR19jVuK5Z0%2BC1SWjoJhRVLKIo4ZkyFT5Eox%2BCqB%2BvsKPCwk%2F94qUum0poAaUAH0kQ%3D%3D';
const pageNo = 1;
const numOfRows = 10;

async function callHospInfo() {
    fetch(`https://apis.data.go.kr/B551182/hospInfoServicev2/getHospBasisList?ServiceKey=${apiKey}&pageNo=${pageNo}&numOfRows=${numOfRows}&_type=json`)
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Network response was not ok');
            }
        })
        .then(data => {
            console.log(data);
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}

