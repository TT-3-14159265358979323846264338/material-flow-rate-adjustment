type CalendarDropDownProps = {
  children: string;
  name?: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
};

const CalendarDropDown = ({ children, name = "calendar", value, onChange }: CalendarDropDownProps) => (
  <div className="flex flex-col items-stretch w-full *:text-center">
    <label>{children}</label>
    <input
      name={name}
      type="date"
      value={value}
      onChange={onChange}
      className="w-full border border-b-black rounded-md focus:outline-none focus:ring-2 focus:ring-amber-200 bg-white mb-2"
    />
  </div>
);

export default CalendarDropDown;