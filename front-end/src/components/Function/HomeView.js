import React from "react";
import QuestionIcon from "@material-ui/icons/Assignment";
import HistoryScoreIcon from "@material-ui/icons/Assessment";
import { Link } from "react-router-dom";

export default class CoursesView extends React.Component {
  render() {
    return (
      <div className="home-container">
        <div className="home-body">
          <div className="home-body__description">
            <div className="greeting">Welcome to Mind Initiative, Yueer</div>
            <div className="description">
              Track Outcomes, Inform Treatment. For Psychologists, Psychiatrists
              & Counsellors.
            </div>
          </div>
          <div className="home-body__utility">
            <Link className="homeButton" to="/progress">
            <button>
              <QuestionIcon style={{ fontSize: "2.5em" }}></QuestionIcon>
              <span>
              Start Questionnaire
              </span>
            </button>
            </Link>
            <Link className="homeButton" to="/result">
            <button>
              <HistoryScoreIcon
                style={{ fontSize: "2.5em" }}
              ></HistoryScoreIcon>
              <span>
              View History Score
              </span>
            </button>
            </Link>
          </div>
        </div>
      </div>
    );
  }
}
