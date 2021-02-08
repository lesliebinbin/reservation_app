import React from "react";
import { withStyles } from "@material-ui/core/styles";
import Radio from "@material-ui/core/Radio";

const SelectRadio = withStyles({
  root: {
    color: "#404156"[400],
    "&$checked": {
      color: "#404156"[600]
    }
  },
  checked: {}
})(props => <Radio color="default" {...props} />);

export default function RadioButtons(props) {
  const [selectedValue, setSelectedValue] = React.useState();

  function handleChange(event) {
    setSelectedValue(event.target.value);
    //
    props.record(props.qnum, event.target.value);
  }

  return (
    <div className="RadiosContainer">
      <SelectRadio
        checked={selectedValue === "3"}
        onChange={handleChange}
        value="3"
        name="radio-button-demo"
        inputProps={{ "aria-label": "Always" }}
      />
      ALMOST ALWAYS
      <SelectRadio
        checked={selectedValue === "2"}
        onChange={handleChange}
        value="2"
        name="radio-button-demo"
        inputProps={{ "aria-label": "often" }}
      />
      OFTEN
      <SelectRadio
        checked={selectedValue === "1"}
        onChange={handleChange}
        value="1"
        name="radio-button-demo"
        inputProps={{ "aria-label": "Sometimes" }}
      />
      SOMETIMES
      <SelectRadio
        checked={selectedValue === "0"}
        onChange={handleChange}
        value="0"
        name="radio-button-demo"
        inputProps={{ "aria-label": "Never" }}
      />
      NEVER
    </div>
  );
}
