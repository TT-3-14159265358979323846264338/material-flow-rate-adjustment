import { useState } from 'react';
import TopPageTab from '../components/TopPageTab';
import { TabConfig } from '../types/tabConfig';
import Logout from '../common-fragment-page/Logout';
import HistoryUser from './fragment-page/HistoryUser';
import CorrectUser from './fragment-page/CorrectUser';
import CorrectMatterial from './fragment-page/CorrectMatterial';

type TabKey =
  | "check-now-material"
  | "check-achievement"
  | "material-management"
  | "history-material"
  | "user-management"
  | "history-user"
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
    id: "history-material",
    label: "製品履歴",
    content: <p>既存の製品を修正する</p>,
  },
  {
    id: "user-management",
    label: "ユーザー管理",
    content: <CorrectUser></CorrectUser>,
  },
  {
    id: "history-user",
    label: "ユーザー履歴",
    content: <HistoryUser></HistoryUser>,
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