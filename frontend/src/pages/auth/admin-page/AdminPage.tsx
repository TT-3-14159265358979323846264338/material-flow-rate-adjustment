import { useState } from 'react';
import TopPageTab from '../components/TopPageTab';
import { TabConfig } from '../types/tabConfig';
import Logout from '../common-page/fragment-page/Logout';
import MaterialManegement from './fragment-page/MaterialManagement';
import UserManegement from './fragment-page/UserManegement';
import AchievementManegement from '../common-page/fragment-page/AchievementManagement';

type TabKey = "top" | "check-achievement" | "material-management" | "user-management" | "account";

const tabData: TabConfig<TabKey>[] = [
  {
    id: "top",
    label: "トップ",
    content: <p>トップ画面</p>,
  },
  {
    id: "check-achievement",
    label: "実績確認",
    content: <AchievementManegement></AchievementManegement>,
  },
  {
    id: "material-management",
    label: "製品管理",
    content: <MaterialManegement></MaterialManegement>,
  },
  {
    id: "user-management",
    label: "ユーザー管理",
    content: <UserManegement></UserManegement>,
  },
  {
    id: "account",
    label: "アカウント",
    content: <Logout></Logout>,
  },
];

const AdminPage = () => {
  const [activeTab, setActiveTab] = useState<TabKey>('top');
  return (
    <TopPageTab activeTab={activeTab} setActiveTab={setActiveTab} tabData={tabData}></TopPageTab>
  );
};

export default AdminPage;