import { TabConfig } from "../types/tabConfig";

type TopPageTabProps<T extends string> = {
  activeTab: T;
  setActiveTab: (id: T) => void;
  tabData: TabConfig<T>[];
}

const TopPageTab = <T extends string>({ activeTab, setActiveTab, tabData }: TopPageTabProps<T>) => {
  return (
    <div className="flex flex-col items-stretch w-200 mx-auto mt-5 text-black">
      <div className="flex">
        {tabData.map((tab) => (
          <button
            key={tab.id}
            onClick={() => setActiveTab(tab.id)}
            className={`
              flex-1 py-2 rounded-t-3xl cursor-pointer
              ${activeTab === tab.id ? 'font-black bg-[#f0aa50]': 'bg-[#ffedbc] text-gray-500 hover:text-black'}
            `}
          >
            {tab.label}
          </button>
        ))}
      </div>
      <div className="h-100 bg-[#f0aa50] border border-black">
        {tabData.find((tab) => tab.id === activeTab)?.content}
      </div>
    </div>
  );
};

export default TopPageTab;