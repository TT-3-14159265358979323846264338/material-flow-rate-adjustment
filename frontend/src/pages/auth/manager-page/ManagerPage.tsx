import { useState } from 'react';
import TopPageTab from '../components/TopPageTab';
import { TabConfig } from '../types/tabConfig';
import Logout from '../common-page/fragment-page/Logout';
import PlanManagement from './fragment-page/PlanManagement';
import CalendarManegement from '../common-page/fragment-page/CalendarManegement';

type TabKey =
  | "check-now-material"
  | "check-achievement"
  | "plan-management"
  | "calendar-management"
  | "account";

const ManagerPage = () => {
  const [activeTab, setActiveTab] = useState<TabKey>('check-now-material');

  const tabData: TabConfig<TabKey>[] = [
    {
      id: "check-now-material",
      label: "流動確認",
      content: <p>現在の流動状況確認画面</p>,
    },
    {
      id: "check-achievement",
      label: "実績確認",
      content: <p>既存の実績を確認する</p>,
    },
    {
      id: "plan-management",
      label: "計画管理",
      content: <PlanManagement></PlanManagement>,
    },
    {
      id: "calendar-management",
      label: "日程管理",
      content: <CalendarManegement></CalendarManegement>,
    },
    {
      id: "account",
      label: "アカウント",
      content: <Logout></Logout>,
    },
  ];

  return (
    <TopPageTab activeTab={activeTab} setActiveTab={setActiveTab} tabData={tabData}></TopPageTab>
  );
};

export default ManagerPage;