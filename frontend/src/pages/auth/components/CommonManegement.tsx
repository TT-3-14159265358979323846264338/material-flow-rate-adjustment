import DefaultButton from "./DefaultButton";
import DefaultModal from "./DefaultModal";

type CommonManegementProps<T, U> = {
  title: string;
  selectedItem: T | undefined;
  setSelectedItem: React.Dispatch<React.SetStateAction<T | undefined>>;
  isOpenSort: boolean;
  setIsOpenSort: React.Dispatch<React.SetStateAction<boolean>>;
  isOpenCorrect: boolean;
  setIsOpenCorrect: React.Dispatch<React.SetStateAction<boolean>>;
  setView: React.Dispatch<React.SetStateAction<U>>;
  mappingData: T[];
  correctHandle: (validator?: (() => void) | undefined) => void;
  comment: React.ReactElement;
  data: (item: T) => React.ReactElement;
  sortModal: React.ReactElement;
  correctModal: React.ReactElement;
};

const CommonManegement = <T extends { id: number }, U extends "New" | "History" | string>({
  title,
  selectedItem,
  setSelectedItem,
  isOpenSort,
  setIsOpenSort,
  isOpenCorrect,
  setIsOpenCorrect,
  setView,
  mappingData,
  correctHandle,
  comment,
  data,
  sortModal,
  correctModal,
}: CommonManegementProps<T, U>) => (
  <div className="flex flex-col items-stretch">
    <div className="flex flex-col h-83">
      <h2>{title}</h2>
      <ul className="border rounded-t-md bg-white">
        <li className="ml-2 mr-2 gap-2 flex items-center *:text-center *:block">{comment}</li>
      </ul>
      <ul className="flex-1 overflow-y-auto border border-b-black rounded-b-md bg-white">
        {mappingData.map((item) => (
          <li
            key={item.id}
            onClick={() => setSelectedItem(item)}
            className={`ml-2 mr-2 gap-2 flex items-center border-b border-b-gray-300 cursor-pointer *:text-center *:block
                  ${item.id === selectedItem?.id ? " bg-gray-200" : " bg-white"}`}
          >
            {data(item)}
          </li>
        ))}
      </ul>
    </div>

    <div className="flex justify-center gap-5">
      <DefaultButton onClick={() => setIsOpenSort(true)}>ソート</DefaultButton>
      <DefaultButton onClick={correctHandle}>登録修正</DefaultButton>
      <DefaultButton onClick={() => setView("New" as U)}>新規登録</DefaultButton>
      <DefaultButton onClick={() => setView("History" as U)}>修正履歴</DefaultButton>
    </div>

    <DefaultModal isOpen={isOpenSort} setIsOpen={setIsOpenSort}>
      {sortModal}
    </DefaultModal>
    <DefaultModal isOpen={isOpenCorrect} setIsOpen={setIsOpenCorrect}>
      {correctModal}
    </DefaultModal>
  </div>
);

export default CommonManegement;