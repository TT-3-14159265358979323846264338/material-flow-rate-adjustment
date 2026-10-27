import DefaultButton from "./DefaultButton";
import DefaultModal from "./DefaultModal";

type CommonHistoryProps<T> = {
  title: string;
  isOpen: boolean;
  setIsOpen: React.Dispatch<React.SetStateAction<boolean>>;
  returnTop: () => void;
  mappingData: T[];
  comment: React.ReactElement;
  data: (item: T) => React.ReactElement;
  modal: React.ReactElement;
};

const CommonHistory = <T extends { id: number }>({
  title,
  isOpen,
  setIsOpen,
  returnTop,
  mappingData,
  comment,
  data,
  modal,
}: CommonHistoryProps<T>) => (
  <div className="flex flex-col">
    <h2>{title}</h2>
    <div className="w-195 h-105 overflow-y-auto overflow-x-auto rounded-s-md bg-white">
      <div className="min-w-max">
        <ul className="sticky top-0 text-sm border rounded-t-md bg-white">
          <li className="flex items-center ml-2 mr-2 *:ml-1 **:block">{comment}</li>
        </ul>
        <ul className="min-h-max text-xs border border-b-black rounded-b-md">
          {mappingData.map((item) => (
            <li
              key={item.id}
              className={`flex min-w-max ml-2 mr-2 items-center border-b border-b-gray-500 
                  *:ml-1 **:block **:min-h-lh`}
            >
              {data(item)}
            </li>
          ))}
        </ul>
      </div>
    </div>

    <div className="flex justify-center gap-5">
      <DefaultButton onClick={() => setIsOpen(true)}>ソート</DefaultButton>
      <DefaultButton onClick={returnTop}>戻る</DefaultButton>
    </div>

    <DefaultModal isOpen={isOpen} setIsOpen={setIsOpen}>
      {modal}
    </DefaultModal>
  </div>
);

export default CommonHistory;