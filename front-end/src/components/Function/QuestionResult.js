import React, { useState, useEffect } from "react";
import { Chart } from "chart.js";
import { Link } from "react-router-dom";

async function getRecordsOfUser(username) {
  const resp = await fetch(
    `/data/api/questionAndAnswerRecords/search/findAllByUserUsername?username=${username}`
  );
  const json = await resp.json();
  const temp = json["_embedded"]["questionAndAnswerRecords"];
  const result = temp.map(v => v["scores"]);
  return result.map(value => value["A"] + value["S"] + value["D"]);
}

const QuestionResult = () => {
  useEffect(() => {
    getRecordsOfUser("yueer").then(resp => {
      var ctx = document.getElementById("myChart");
      console.log(resp);
      var myChart = new Chart(ctx, {
        type: "line",
        data: {
          labels: [...Array(resp.length).keys()].map(i => i + 1),
          datasets: [
            {
              label: "Your Score History",
              data: resp,
              backgroundColor: "rgba(113, 147, 204, 0.3)",
              borderColor: "#7193cc",
              borderWidth: 1.2
            }
          ]
        },
        options: {
          scales: {
            yAxes: [
              {
                ticks: {
                  beginAtZero: true
                },
                scaleLabel: {
                  display: true,
                  labelString: 'Scores'
              }
              }
            ],
            xAxes: [
              {
                ticks: {
                  beginAtZero: true
                },
                scaleLabel: {
                  display: true,
                  labelString: 'Times'
              }
              }
            ]
          }
        }
      });
    });
  });

  const href = window.location.href;
  console.log(href)
  let resultCurrent = null;
  let resultSum = 0;
  if (href.split("=").length === 1) {
    console.log("error");
  } else {
    resultCurrent = JSON.parse(window.atob(href.split("=")[1]));
    Object.keys(resultCurrent).forEach(key => {
      resultSum += resultCurrent[[key]];
    });
  }

  function justifyStatus() {
    const status = [];
    const D = resultCurrent["D"];
    const A = resultCurrent["A"];
    const S = resultCurrent["S"];
    if (D <= 9) {
      status.push("Normal");
    } else if (D <= 13) {
      status.push("Mild");
    } else if (D <= 20) {
      status.push("Moderate");
    } else if (D <= 27) {
      status.push("Severe");
    } else {
      status.push("Extremely Severe");
    }
    if (A <= 7) {
      status.push("Normal");
    } else if (A <= 9) {
      status.push("Mild");
    } else if (A <= 14) {
      status.push("Moderate");
    } else if (A <= 19) {
      status.push("Severe");
    } else {
      status.push("Extremely Severe");
    }
    if (S <= 14) {
      status.push("Normal");
    } else if (S <= 18) {
      status.push("Mild");
    } else if (S <= 25) {
      status.push("Moderate");
    } else if (S <= 33) {
      status.push("Severe");
    } else {
      status.push("Extremely Severe");
    }
    return status;
  }

  if (resultCurrent === null) {
    return (
      <div className="resultContainer">
        <div className="scoreContainer">
          <div className="scoreDisplay">
            Your Result: <span className="questionResult">NaN</span>
          </div>
          <div className="statusDisplay">
            <table>
              <tbody>
                <tr>
                  <th>Depression</th>
                  <th>Anxiety</th>
                  <th>Stress</th>
                </tr>
                <tr>
                  <td>NaN</td>
                  <td>NaN</td>
                  <td>NaN</td>
                </tr>
              </tbody>
            </table>
          </div>
          <div>
            Start another test:
            <Link to="/progress">Click here</Link>
          </div>
        </div>
        <canvas id="myChart" width="55vw"></canvas>
      </div>
    );
  } else {
    return (
      <div className="resultContainer">
        <div className="scoreContainer">
          <div className="scoreDisplay">
            Your Result: <span className="questionResult">{resultSum}</span>
          </div>
          <div className="statusDisplay">
            <table>
              <tbody>
                <tr>
                  <th>Depression</th>
                  <th>Anxiety</th>
                  <th>Stress</th>
                </tr>
                <tr>
                  <td>{justifyStatus()[0]}</td>
                  <td>{justifyStatus()[1]}</td>
                  <td>{justifyStatus()[2]}</td>
                </tr>
              </tbody>
            </table>
          </div>
          <div>
            For more information:
            <a href="https://maic.qld.gov.au/wp-content/uploads/2016/07/DASS-21.pdf">
              Click here
            </a>
          </div>
        </div>
        <canvas id="myChart" height="40vh" width="80vw"></canvas>
      </div>
    );
  }
};

export default QuestionResult;
