let datepickers = document.querySelectorAll("[name='datepicker']");
let thirtyDaysFromToday = new Date();
thirtyDaysFromToday.setDate(thirtyDaysFromToday.getDate() + 30);

let now = new Date();
let timezoneOffset = now.getTimezoneOffset();
let koreanTimezoneOffset = -540;
let timezoneDiff = koreanTimezoneOffset - timezoneOffset;
let koreanTime = new Date(now.getTime() + timezoneDiff * 60 * 1000);
koreanTime.setHours(koreanTime.getHours() + 1);

for (let i = 0; i < datepickers.length; i++) {
    flatpickr(datepickers[i], {
        minDate: new Date(),
        maxDate: thirtyDaysFromToday,
        enableTime: true,
        noCalendar: false,
        dateFormat: "Y-m-d H:i",
        time_24hr: true,
        minTime: "00:00",
        maxTime: "23:59",
        defaultHour: koreanTime.getHours(),
        locale: {
            firstDayOfWeek: 0,
            weekdays: {
                shorthand: ["일", "월", "화", "수", "목", "금", "토"],
                longhand: ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"]
            },
            months: {
                shorthand: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
                longhand: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"]
            },
            rangeSeparator: " ~ ",
            scrollTitle: "스크롤하여 보기",
            toggleTitle: "클릭하여 열기/닫기"
        },
        hourIncrement: 1,
        minuteIncrement: 60
    });
}