$(document).ready(function() {
    $.getJSON('/rate', function(data) {
        $('#rate-uah').text(data.rates.UAH);
        $('#rate-usd').text(data.rates.USD);
        $('#date-uah').text(data.date);
        $('#date-usd').text(data.date);
    });
});