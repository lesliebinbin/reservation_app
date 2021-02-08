window.addEventListener('load', event => {
    clockIn()
    getCurrentVisited()
})

function clockIn() {
    fetch('http://167.71.198.93:8082/informative/visited/', {
        method: 'POST',
        mode: 'no-cors'
    })
}

async function getCurrentVisited() {
    fetch('http://167.71.198.93:8082/informative/shareIt', {
            method: 'GET',
            mode: 'no-cors'
        }).then(resp => resp.text())
        .then(data => console.log(data))
}