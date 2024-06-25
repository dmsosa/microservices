export function calculateSavings() {
    const incomes = Number($(".metric.metric-incomes .metric-value").text())
    const expenses = Number($(".metric.metric-expenses .metric-value").text());
    $(".metric.metric-savings .metric-value").html(Number(incomes - expenses).toString());
}

