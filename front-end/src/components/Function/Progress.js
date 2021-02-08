import React from "react";
import Radios from "./Radios";
import Submit from "@material-ui/core/Button";
import { Link } from "react-router-dom";
import IconButton from "@material-ui/core/IconButton";
import NavigateNextIcon from "@material-ui/icons/NavigateNext";

const records = {};
export default class Progress extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      DoneStatus: false,
      questions: [],
      questionChosen: [],
      pageIndex: 0,
      pageCount: 6
    };
  }

  componentDidMount() {
    this.fetchQuesionnaireByDescription("DASS 21");
  }

  recordClick(key, value) {
    records[key] = value;
    console.log(records);
  }

  async fetchQuesionnaireByDescription(description) {
    const url = `/data/api/questionnaires/search/findFirstByDescription?description=${description}`;
    const resp = await fetch(url);
    const jsonData = await resp.json();
    const { questions } = jsonData;

    this.setState({ questions });
  }

  async submitAnswers() {
    const { questions } = this.state;
    if (Object.keys(records).length !== questions.length) {
      alert("Please fulfill all the questions before going to next page");
      return;
    } else {
      const url = `/data/scores`;
      const choices = [];
      for (let k in records) {
        choices.push({ [k]: records[k] });
      }
      console.log(choices);
      const resp = await fetch(url, {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          username: "yueer",
          questionnaire: "DASS 21",
          choices: choices
        })
      });
      const jsonData = await resp.json();
      console.log(jsonData);
      console.log('location');
      console.log(this.props.location);
      this.props.history.push(`/result?result=${window.btoa(
        JSON.stringify(jsonData)
      )}`)
      //window.location.href = `/result?result=${window.btoa(
       // JSON.stringify(jsonData)
      //)}`;
    }
  }

  loadNext() {
    const { pageIndex, questions, pageCount } = this.state;
    if (Object.keys(records).length === (pageIndex + 1) * pageCount) {
      const countOfQuestions = questions.length;
      const countOfPages = countOfQuestions / pageCount + 1;
      const newPageIndex = (pageIndex + 1) % countOfPages;
      this.setState({ pageIndex: newPageIndex });
    } else {
      alert("Please fulfill all the questions before going to next page");
    }
  }

  render() {
    const questions = this.state.questions;
    const { pageIndex, pageCount } = this.state;
    const elements = questions
      .slice(pageIndex * pageCount, (pageIndex + 1) * pageCount)
      .map((value, index) => (
        <div className="questions" key={index}>
          <span>
            {pageIndex * pageCount + 1 + index}. {value.description}
            <Radios
              key={pageIndex * pageCount + 1 + index}
              qnum={pageIndex * pageCount + 1 + index}
              record={this.recordClick}
            />
          </span>
        </div>
      ));

    const pageNumber = Math.ceil(questions.length / pageCount);
    if (this.state.pageIndex === pageNumber - 1) {
      return (
        <div className="questionContainer">
          {elements}
          <span className="submitButtom">
            <Submit variant="contained" onClick={this.submitAnswers.bind(this)}>
              Submit
            </Submit>
          </span>
        </div>
      );
    } else {
      return (
        <div className="questionContainer">
          {elements}
          <span className="nextButton">
            <span>Next</span>
            <IconButton onClick={this.loadNext.bind(this)}>
              <NavigateNextIcon />
            </IconButton>
          </span>
        </div>
      );
    }
  }
}
