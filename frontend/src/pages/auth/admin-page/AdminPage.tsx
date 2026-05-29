import { useState } from 'react';
import TopPageTab from '../components/TopPageTab';
import { TabConfig } from '../types/tabConfig';
import Logout from '../common-fragment-page/Logout';
import CorrectUser from './fragment-page/CorrectUser';
import CorrectMatterial from './fragment-page/CorrectMaterial';

type TabKey =
  | "check-now-material"
  | "check-achievement"
  | "material-management"
  | "user-management"
  | "logout";

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
    id: "material-management",
    label: "製品管理",
    content: <CorrectMatterial></CorrectMatterial>,
  },
  {
    id: "user-management",
    label: "ユーザー管理",
    content: <CorrectUser></CorrectUser>,
  },
  {
    id: "logout",
    label: "ログアウト",
    content: <Logout></Logout>,
  },
];

const AdminPage = () => {
  const [activeTab, setActiveTab] = useState<TabKey>('check-now-material');
  return (
    <TopPageTab activeTab={activeTab} setActiveTab={setActiveTab} tabData={tabData}></TopPageTab>
  );
};

export default AdminPage;