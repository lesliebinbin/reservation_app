import React from "react";
import ReactDOM from "react-dom";
import { shallow } from "enzyme";
import renderer from "react-test-renderer";
import Brand from "./components/App/Brand";

test("test", () => {
  expect(1 + 2).toBe(3);
});

test("01. start questionnaire button", () => {
  const content = "Mind Initiative";
  let component = renderer.create(<Brand />);
  expect(component.find("span").text()).toEqual(content);
});
