const UserInput = ({value = "", onChange = () => {}}) => (
  <div className="flex flex-col items-stretch">
    <label>ユーザー名</label>
    <input
      type="text"
      placeholder="user name"
      value={value}
      onChange={onChange}
      className="border border-b-black rounded-md focus:outline-none focus:ring-2 focus:ring-amber-200 bg-white mb-2"
      required
    />
  </div>
)

export default UserInput;