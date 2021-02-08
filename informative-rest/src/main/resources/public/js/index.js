window.addEventListener("load", event => {
  clockIn();
  getCurrentVisited(document.getElementById("count"));
  getCountOfLike(document.getElementById("countOfLike"));
  getCountOfShare(document.getElementById("countOfShare"));
});

function clockIn() {
  fetch("/visited/", {
    method: "POST"
  });
}

async function getCurrentVisited(element) {
  const data = await fetch("/visited/count", {
    method: "GET"
  });
  const json = await data.json();
  console.log(json.count);
  element.innerText = json.count;
}

async function getCountOfLike(element) {
  const data = await fetch("/likeIt");
  const json = await data.json();
  element.innerText = json.length;
}

async function getCountOfShare(element) {
  const data = await fetch("/shareIt");
  const json = await data.json();
  element.innerText = json.length;
}
