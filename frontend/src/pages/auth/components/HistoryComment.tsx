type HistoryCommentProps = {
  w: number;
  comment: string;
};

const HistoryComment = ({ w, comment }: HistoryCommentProps) => (
  <div className={`w-${w}`}>
    <span className="border-b border-b-gray-200">{`前 ${comment}`}</span>
    <span>{`後 ${comment}`}</span>
  </div>
);

export default HistoryComment;