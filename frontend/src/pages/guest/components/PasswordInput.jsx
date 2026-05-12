const PasswordInput = ({value = "", onChange = () => {}}) => (
  <div className="flex flex-col items-stretch">
    <label>パスワード</label>
    <input
      type="password"
      placeholder="••••••••"
      value={value}
      onChange={onChange}
      className="border border-b-black rounded-md focus:outline-none focus:ring-2 focus:ring-amber-200 bg-white mb-7"
      required
    />
  </div>
)

export default PasswordInput;