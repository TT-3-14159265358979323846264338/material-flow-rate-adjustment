type HistoryDataProps = {
  w: number;
  before: any;
  after: any;
  trans?: (code: any | null) => string | undefined;
};

const HistoryData = ({ w, before, after, trans }: HistoryDataProps) => (
  <div className={`w-${w}`}>
    <span className="border-b border-b-gray-200">{trans ? trans(before) : before}</span>
    <span>{trans ? trans(after) : after}</span>
  </div>
);

export default HistoryData;