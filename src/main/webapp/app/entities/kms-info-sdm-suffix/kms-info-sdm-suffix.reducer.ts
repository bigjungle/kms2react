import axios from 'axios';
import {
  ICrudSearchAction,
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IKmsInfoSdmSuffix, defaultValue } from 'app/shared/model/kms-info-sdm-suffix.model';

export const ACTION_TYPES = {
  SEARCH_KMSINFOS: 'kmsInfo/SEARCH_KMSINFOS',
  FETCH_KMSINFO_LIST: 'kmsInfo/FETCH_KMSINFO_LIST',
  FETCH_KMSINFO: 'kmsInfo/FETCH_KMSINFO',
  CREATE_KMSINFO: 'kmsInfo/CREATE_KMSINFO',
  UPDATE_KMSINFO: 'kmsInfo/UPDATE_KMSINFO',
  DELETE_KMSINFO: 'kmsInfo/DELETE_KMSINFO',
  SET_BLOB: 'kmsInfo/SET_BLOB',
  RESET: 'kmsInfo/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IKmsInfoSdmSuffix>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type KmsInfoSdmSuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: KmsInfoSdmSuffixState = initialState, action): KmsInfoSdmSuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_KMSINFOS):
    case REQUEST(ACTION_TYPES.FETCH_KMSINFO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_KMSINFO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_KMSINFO):
    case REQUEST(ACTION_TYPES.UPDATE_KMSINFO):
    case REQUEST(ACTION_TYPES.DELETE_KMSINFO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_KMSINFOS):
    case FAILURE(ACTION_TYPES.FETCH_KMSINFO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_KMSINFO):
    case FAILURE(ACTION_TYPES.CREATE_KMSINFO):
    case FAILURE(ACTION_TYPES.UPDATE_KMSINFO):
    case FAILURE(ACTION_TYPES.DELETE_KMSINFO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_KMSINFOS):
    case SUCCESS(ACTION_TYPES.FETCH_KMSINFO_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_KMSINFO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_KMSINFO):
    case SUCCESS(ACTION_TYPES.UPDATE_KMSINFO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_KMSINFO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB:
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/kms-infos';
const apiSearchUrl = 'api/_search/kms-infos';

// Actions

export const getSearchEntities: ICrudSearchAction<IKmsInfoSdmSuffix> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_KMSINFOS,
  payload: axios.get<IKmsInfoSdmSuffix>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IKmsInfoSdmSuffix> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_KMSINFO_LIST,
    payload: axios.get<IKmsInfoSdmSuffix>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IKmsInfoSdmSuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_KMSINFO,
    payload: axios.get<IKmsInfoSdmSuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IKmsInfoSdmSuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_KMSINFO,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IKmsInfoSdmSuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_KMSINFO,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IKmsInfoSdmSuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_KMSINFO,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
